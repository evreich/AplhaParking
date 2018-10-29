using AlphaParking.DB.Models;
using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Linq;
using AlphaParking.DB.Models.SeedData;
using AlphaParking.DAL.Repositories;
using AutoMapper;
using AlphaParking.BLL.Services.DTO;
using AlphaParking.BLL.Services.Utils;

namespace AlphaParking.BLL.Services
{
    public class UserService : IUserService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IMapper _mapper;

        public UserService(IUnitOfWork unitOfWork, IMapper mapper)
        {
            _unitOfWork = unitOfWork;
            _mapper = mapper;
        }

        private User MapUserFromDTO(UserDTO elem)
        {
            var hasher = new PasswordHasher<User>();
            var user = _mapper.Map<User>(elem);
            var passwordHash = hasher.HashPassword(user, user.Password);
            user.Password = passwordHash;
            return user;
        }

        public async Task<UserDTO> Create(UserDTO elem)
        {
            if(await IsRegistered(elem.Login, elem.Password))
            {
                throw new ArgumentException("Пользователь уже существет");
            }
            var newUser = _mapper.Map<UserDTO>(await _unitOfWork.UserRepository.Create(MapUserFromDTO(elem)));

            var defaultRole = _mapper.Map<RoleDTO>(await _unitOfWork.RoleRepository.GetElem(role => role.Name == RoleConstants.EMPLOYEE));
            this.AddRoleToUser(newUser, defaultRole);
            // await _unitOfWork.UserRoleRepository.Create(new UserRole { RoleId = defaultRole.Id, UserId = newUser.Id });

            return _mapper.Map<UserDTO>(newUser);
        }

        public async Task<UserDTO> Update(UserDTO elem)
        {
            var updatedUser = await _unitOfWork.UserRepository.Update(MapUserFromDTO(elem));
            return _mapper.Map<UserDTO>(updatedUser);
        }

        public async Task<UserDTO> Delete(UserDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<UserDTO, User>
                (_unitOfWork.UserRepository.Delete, elem, _mapper);
        }

        public async Task<IEnumerable<UserDTO>> GetAll()
        {
            return _mapper.Map<IEnumerable<User>, IEnumerable<UserDTO>>
                (await _unitOfWork.UserRepository.GetElems(elem => elem.UserCars));
        }

        public async Task<UserDTO> GetUser(Guid userId)
        {
            return _mapper.Map<UserDTO>
                (await _unitOfWork.UserRepository.GetElem(elem => elem.Id == userId, 
                elem => elem.UserCars, elem => elem.UserRoles));
        }

        public async Task<IEnumerable<ParkingSpaceDTO>> GetUserParkingPlaces(Guid userId)
        {
            var cars = _mapper.Map<UserDTO>((await _unitOfWork.UserRepository.GetElem(elem => elem.Id == userId, elem => elem.UserCars))).Cars;
            return _mapper.Map<IEnumerable<ParkingSpace>,IEnumerable<ParkingSpaceDTO>>
                ((await _unitOfWork.ParkingSpaceCarRepository
                .GetElems(elem => cars.Any(uc => uc.Number == elem.CarNumber || uc.Number == elem.DelegatedCarNumber)))
                .Select(elem => elem.ParkingSpace));
        }

        public void AddRoleToUser(UserDTO user, RoleDTO role)
        {
            user.Roles.Add(role);
            _unitOfWork.UserRepository.Update(_mapper.Map<User>(user));
        }

        public async Task<bool> IsRegistered(string login, string password)
        {
            var hasher = new PasswordHasher<User>();
            return await _unitOfWork.UserRepository.GetElem(u => u.Login.Equals(login) &&
                            hasher.VerifyHashedPassword(u, u.Password, password) == PasswordVerificationResult.Success) != null;
        }

        public void Dispose()
        {
            _unitOfWork.Dispose();
        }

        public async Task<IEnumerable<CarDTO>> GetUserCars(Guid userId)
        {
            return _mapper.Map<UserDTO>(await _unitOfWork.UserRepository.GetElem(u => u.Id == userId)).Cars;
        }
    }
}
