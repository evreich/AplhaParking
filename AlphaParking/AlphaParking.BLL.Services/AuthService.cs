using AlphaParking.BLL.Services.Settings;
using AlphaParking.DB.Models;
using Microsoft.IdentityModel.Tokens;
using System;
using System.Linq;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using AlphaParking.BLL.Services.DTO;
using AlphaParking.DAL.Repositories;
using AutoMapper;

namespace AlphaParking.BLL.Services
{
    public class AuthService: IAuthService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IUserService _userService;
        private readonly string _audienceJWT;
        private readonly IMapper _mapper;

        public AuthService(IUnitOfWork uow, IUserService userService, string audience, IMapper mapper)
        {
            _unitOfWork = uow;
            _userService = userService;
            _audienceJWT = audience;
            _mapper = mapper;
        }

        public string GenerateJwtToken(UserDTO user)
        {
            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.Email, user.Email),
                new Claim(ClaimTypes.Sid, Guid.NewGuid().ToString()),
                new Claim(ClaimTypes.NameIdentifier, user.FIO),
            };
            claims.AddRange(user.Roles.Select(r => new Claim(ClaimTypes.Role, r.Name)).ToList());

            var key = AuthOptions.GetSymmetricSecurityKey();
            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
            var expires = DateTime.Now.AddHours(Convert.ToDouble(AuthOptions.LIFETIME));

            var token = new JwtSecurityToken(
                AuthOptions.ISSUER,
                _audienceJWT,
                claims,
                expires: expires,
                signingCredentials: creds
            );

            return new JwtSecurityTokenHandler().WriteToken(token);
        }

        // TODO: сделать DI сервиса на Web, 
        // сделать Startup окончательно, приступить к контроллерам, реализовать ViewModels and AutoMapper
        // дропать localStorage на клиенте на логауте
        public async Task<AuthInfo> Login(string login, string password)
        {
            UserDTO user = _mapper.Map<UserDTO>(await _unitOfWork.UserRepository.GetElem(u => u.Login.Equals(login)));
            if (user == null)
            {
                throw new Exception("Данный пользователь не зарегистрирован");
            }
            if (!await _userService.IsRegistered(login, password))
            {
                throw new Exception("Неверный пароль");
            }

            var userToken = this.GenerateJwtToken(user);

            return new AuthInfo
            {
                Name = user.FIO,
                Login = user.Login,
                Token = userToken,
                Roles = user.Roles.Select(r => r.Name).ToArray()
            };
        }

        public async Task<AuthInfo> Registration(UserDTO user)
        {
            UserDTO createdUser = await _userService.Create(user);
            var userToken = this.GenerateJwtToken(createdUser);

            return new AuthInfo
            {
                Name = createdUser.FIO,
                Login = createdUser.Login,
                Token = userToken,
                Roles = createdUser.Roles.Select(r => r.Name).ToArray()
            };
        }

        public void Dispose()
        {
            _unitOfWork.Dispose();
        }
    }
}
