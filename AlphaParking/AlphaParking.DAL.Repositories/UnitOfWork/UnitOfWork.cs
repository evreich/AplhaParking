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

        private CRUDRepository<Role> _roleRepository;
        private CRUDRepository<UserRole> _userRoleRepository;
        private CRUDRepository<User> _userRepository;
        private CRUDRepository<UserCar> _userCarRepository;
        private CRUDRepository<Car> _carRepository;
        private CRUDRepository<ParkingSpace> _parkingSpaceRepository;
        private CRUDRepository<ParkingSpaceCar> _parkingSpaceCarRepository;
        private CRUDRepository<TempOwnerParkingSpace> _tempOwnerParkingSpaceRepository;

        public UnitOfWork(AlphaParkingDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public ICRUDRepository<Role> RoleRepository
        {
            get
            {
                return _roleRepository ?? new CRUDRepository<Role>(_dbContext);
            }
        }

        public ICRUDRepository<UserRole> UserRoleRepository
        {
            get
            {
                return _userRoleRepository ?? new CRUDRepository<UserRole>(_dbContext);
            }
        }

        public ICRUDRepository<User> UserRepository
        {
            get
            {
                return _userRepository ?? new CRUDRepository<User>(_dbContext);
            }
        }

        public ICRUDRepository<UserCar> UserCarRepository
        {
            get
            {
                return _userCarRepository ?? new CRUDRepository<UserCar>(_dbContext);
            }
        }

        public ICRUDRepository<Car> CarRepository
        {
            get
            {
                return _carRepository ?? new CRUDRepository<Car>(_dbContext);
            }
        }

        public ICRUDRepository<ParkingSpace> ParkingSpaceRepository
        {
            get
            {
                return _parkingSpaceRepository ?? new CRUDRepository<ParkingSpace>(_dbContext);
            }
        }

        public ICRUDRepository<ParkingSpaceCar> ParkingSpaceCarRepository
        {
            get
            {
                return _parkingSpaceCarRepository ?? new CRUDRepository<ParkingSpaceCar>(_dbContext);
            }
        }

        public ICRUDRepository<TempOwnerParkingSpace> TempOwnerParkingSpaceRepository
        {
            get
            {
                return _tempOwnerParkingSpaceRepository ?? new CRUDRepository<TempOwnerParkingSpace>(_dbContext);
            }
        }

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
