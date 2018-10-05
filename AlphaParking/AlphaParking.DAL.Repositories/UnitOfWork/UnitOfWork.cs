using AlphaParking.DAL.Interfaces;
using AlphaParking.DB.DbContext.Models;
using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.DAL.Repositories.UnitOfWork
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly AlphaParkingDbContext _dbContext;

        public UnitOfWork(AlphaParkingDbContext dbContext, ICRUDRepository<Role> roleRepository,
            ICRUDRepository<UserRole> userRoleRepository, ICRUDRepository<User> userRepository,
            ICRUDRepository<UserCar> userCarRepository, ICRUDRepository<Car> carRepository,
            ICRUDRepository<ParkingSpace> parkingSpaceRepository, ICRUDRepository<ParkingSpaceCar> parkingSpaceCarRepository,
            ICRUDRepository<TempOwnerParkingSpace> tempParkingSpaceRepository)
        {
            _dbContext = dbContext;
            RoleRepository = roleRepository;
            UserRoleRepository = userRoleRepository;
            UserRepository = userRepository;
            UserCarRepository = userCarRepository;
            CarRepository = carRepository;
            ParkingSpaceCarRepository = parkingSpaceCarRepository;
            ParkingSpaceRepository = parkingSpaceRepository;
            TempOwnerParkingSpaceRepository = tempParkingSpaceRepository;
        }

        public ICRUDRepository<Role> RoleRepository { get; }

        public ICRUDRepository<UserRole> UserRoleRepository { get; }

        public ICRUDRepository<User> UserRepository { get; }

        public ICRUDRepository<UserCar> UserCarRepository { get; }

        public ICRUDRepository<Car> CarRepository { get; }

        public ICRUDRepository<ParkingSpace> ParkingSpaceRepository { get; }

        public ICRUDRepository<ParkingSpaceCar> ParkingSpaceCarRepository { get; }

        public ICRUDRepository<TempOwnerParkingSpace> TempOwnerParkingSpaceRepository { get; }

        public void Dispose()
        {
            _dbContext.Dispose();
        }

        public void Save()
        {
            _dbContext.SaveChanges();
        }
    }
}
