using AlphaParking.DAL.Interfaces;
using AlphaParking.DAL.Repositories.UnitOfWork;
using AlphaParking.DB.DbContext.Models;
using AlphaParking.DB.Models.SeedData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AlphaParking.BLL.Services
{
    public class SeedDbService
    {
        private readonly IUnitOfWork _uow;

        public SeedDbService(IUnitOfWork uow)
        {
            _uow = uow;
        }

        public async void EnsurePopulated()
        {
            if (!(await _uow.UserRepository.GetElems()).Any())
            {
                await _uow.UserRepository.Create(UserConstants.Employee);
                await _uow.UserRepository.Create(UserConstants.Manager);
            }

            if (!(await _uow.RoleRepository.GetElems()).Any())
            {
                await _uow.RoleRepository.Create(RoleConstants.Employee);
                await _uow.RoleRepository.Create(RoleConstants.Manager);
            }

            if (!(await _uow.UserRoleRepository
                            .GetElems(elem => elem.UserId == UserConstants.Employee.Id ||
                                              elem.UserId == UserConstants.Manager.Id))
                            .Any())
            {
                await _uow.UserRoleRepository.Create(new DB.Models.UserRole
                {
                    UserId = UserConstants.Manager.Id,
                    RoleId = RoleConstants.Manager.Id
                });
                await _uow.UserRoleRepository.Create(new DB.Models.UserRole
                {
                    UserId = UserConstants.Employee.Id,
                    RoleId = RoleConstants.Employee.Id
                });
            }

            if (!(await _uow.CarRepository.GetElems()).Any())
            {
                await _uow.CarRepository.Create(CarConstants.Priora);
                await _uow.CarRepository.Create(CarConstants.Solaris);
            }

            if (!(await _uow.UserCarRepository
                .GetElems(elem => elem.UserId == UserConstants.Employee.Id ||
                                  elem.UserId == UserConstants.Manager.Id))
                .Any())
            {
                await _uow.UserCarRepository.Create(new DB.Models.UserCar
                {
                    UserId = UserConstants.Manager.Id,
                    CarNumber = CarConstants.Priora.Number
                });
                await _uow.UserCarRepository.Create(new DB.Models.UserCar
                {
                    UserId = UserConstants.Employee.Id,
                    CarNumber = CarConstants.Solaris.Number
                });
            }

            if (!(await _uow.ParkingSpaceRepository.GetElems()).Any())
            {
                await _uow.ParkingSpaceRepository.Create(ParkingSpaceConstants.ParkingSpaceOne);
                await _uow.ParkingSpaceRepository.Create(ParkingSpaceConstants.ParkingSpaceTwo);
            }

            if (!(await _uow.ParkingSpaceCarRepository
                .GetElems(elem => elem.CarNumber == CarConstants.Solaris.Number ||
                                  elem.CarNumber == CarConstants.Priora.Number))
                .Any())
            {
                await _uow.ParkingSpaceCarRepository.Create(new DB.Models.ParkingSpaceCar
                {
                    ParkingSpaceNumber = ParkingSpaceConstants.ParkingSpaceOne.Number,
                    CarNumber = CarConstants.Priora.Number
                });
                await _uow.ParkingSpaceCarRepository.Create(new DB.Models.ParkingSpaceCar
                {
                    ParkingSpaceNumber = ParkingSpaceConstants.ParkingSpaceTwo.Number,
                    CarNumber = CarConstants.Solaris.Number
                });
            }
        }
    }
}
