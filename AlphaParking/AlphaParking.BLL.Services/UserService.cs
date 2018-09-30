using AlphaParking.BLL.Interfaces;
using AlphaParking.DAL.Interfaces;
using AlphaParking.DB.Models;
using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Linq;
using AlphaParking.DB.Models.SeedData;

namespace AlphaParking.BLL.Services
{
    public class UserService : IUserService
    {
        private readonly IUnitOfWork _unitOfWork;
        public UserService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<User> Create(User elem)
        {
            var hasher = new PasswordHasher<User>();

            if(await IsRegistered(elem.Login, elem.Password) != null)
            {
                throw new ArgumentException("Пользователь уже существет");
            }

            var passwordHash = hasher.HashPassword(elem, elem.Password);
            elem.Password = passwordHash;
            var newUser = await _unitOfWork.UserRepository.Create(elem);

            var defaultRole = await _unitOfWork.RoleRepository.GetElem(role => role.Name == RoleConstants.EMPLOYEE);
            await _unitOfWork.UserRoleRepository.Create(new UserRole { RoleId = defaultRole.Id, UserId = newUser.Id });

            return newUser;
        }

        public async Task<User> Update(User elem)
        {
            var hasher = new PasswordHasher<User>();
            var passwordHash = hasher.HashPassword(elem, elem.Password);
            elem.Password = passwordHash;
            var updatedUser = await _unitOfWork.UserRepository.Update(elem);
            return updatedUser;
        }

        public async Task<User> Delete(User elem)
        {
            return await _unitOfWork.UserRepository.Delete(elem);
        }

        public async Task<IEnumerable<User>> GetAll()
        {
            return await _unitOfWork.UserRepository.GetElems(elem => elem.UserCars);
        }

        public async Task<User> GetUser(Guid userId)
        {
            return await _unitOfWork.UserRepository.GetElem(elem => elem.Id == userId, 
                elem => elem.UserCars, elem => elem.UserRoles);
        }

        public async Task<IEnumerable<ParkingSpace>> GetUserParkingPlaces(Guid userId)
        {
            var userCars = (await _unitOfWork.UserRepository.GetElem(elem => elem.Id == userId, elem => elem.UserCars)).UserCars;
            return (await _unitOfWork.ParkingSpaceCarRepository
                .GetElems(elem => userCars.Any(uc => uc.CarNumber == elem.CarNumber ||
                                    uc.CarNumber == elem.TempOwnerParkingSpace.CarNumber)))
                .Select(elem => elem.ParkingSpace);
        }

        public async Task<User> Login(string login, string password)
        {

            var user = await IsRegistered(login, password);
            if (user == null)
            {
                throw new ArgumentException("Данного пользователя не существует");
            }
            return user;
        }

        private async Task<User> IsRegistered(string login, string password)
        {
            var hasher = new PasswordHasher<User>();
            return await _unitOfWork.UserRepository.GetElem(u => u.Login.Equals(login) &&
                            hasher.VerifyHashedPassword(u, u.Password, password) == PasswordVerificationResult.Success);
        }

        public void Dispose()
        {
            _unitOfWork.Dispose();
        }
    }
}
