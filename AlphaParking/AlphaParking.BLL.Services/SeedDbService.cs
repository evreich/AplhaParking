using AlphaParking.DAL.Repositories;
using AlphaParking.DAL.Repositories.UnitOfWork;
using AlphaParking.DB.DbContext.Models;
using AlphaParking.DB.Models.SeedData;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services
{
    public class SeedDbService: ISeedDbService
    {
        private readonly IUnitOfWork _database;

        public SeedDbService(IUnitOfWork uow)
        {
            _database = uow;
        }

        public async Task EnsurePopulated()
        {
            if (!(await _database.UserRepository.GetElems()).Any())
            {
                await _database.UserRepository.Create(UserConstants.Employee);
                await _database.UserRepository.Create(UserConstants.Manager);
            }

            if (!(await _database.RoleRepository.GetElems()).Any())
            {
                await _database.RoleRepository.Create(RoleConstants.Employee);
                await _database.RoleRepository.Create(RoleConstants.Manager);
            }

            if (!(await _database.UserRoleRepository
                            .GetElems(elem => elem.UserId == UserConstants.Employee.Id ||
                                              elem.UserId == UserConstants.Manager.Id))
                            .Any())
            {
                await _database.UserRoleRepository.Create(new DB.Models.UserRole
                {
                    UserId = UserConstants.Manager.Id,
                    RoleId = RoleConstants.Manager.Id
                });
                await _database.UserRoleRepository.Create(new DB.Models.UserRole
                {
                    UserId = UserConstants.Employee.Id,
                    RoleId = RoleConstants.Employee.Id
                });
            }

            if (!(await _database.CarRepository.GetElems()).Any())
            {
                await _database.CarRepository.Create(CarConstants.Priora);
                await _database.CarRepository.Create(CarConstants.Solaris);
            }

            if (!(await _database.ParkingSpaceRepository.GetElems()).Any())
            {
                await _database.ParkingSpaceRepository.Create(ParkingSpaceConstants.ParkingSpaceOne);
                await _database.ParkingSpaceRepository.Create(ParkingSpaceConstants.ParkingSpaceTwo);
            }

            if (!(await _database.ParkingSpaceCarRepository
                .GetElems(elem => elem.CarNumber == CarConstants.Solaris.Number ||
                                  elem.CarNumber == CarConstants.Priora.Number))
                .Any())
            {
                await _database.ParkingSpaceCarRepository.Create(new DB.Models.ParkingSpaceCar
                {
                    ParkingSpaceNumber = ParkingSpaceConstants.ParkingSpaceOne.Number,
                    CarNumber = CarConstants.Priora.Number,
                    StartParkingTime = new TimeSpan(8,0,0),
                    EndParkingTime = new TimeSpan(17, 0, 0)
                });
                await _database.ParkingSpaceCarRepository.Create(new DB.Models.ParkingSpaceCar
                {
                    ParkingSpaceNumber = ParkingSpaceConstants.ParkingSpaceTwo.Number,
                    CarNumber = CarConstants.Solaris.Number,
                    StartParkingTime = new TimeSpan(8, 0, 0),
                    EndParkingTime = new TimeSpan(17, 0, 0)
                });
            }
        }
    }
}
