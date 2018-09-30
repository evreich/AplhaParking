using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.DAL.Interfaces
{
    public interface IUnitOfWork: IDisposable
    {
        ICRUDRepository<Role> RoleRepository { get; }
        ICRUDRepository<UserRole> UserRoleRepository { get; }
        ICRUDRepository<User> UserRepository { get; }
        ICRUDRepository<UserCar> UserCarRepository { get; }
        ICRUDRepository<Car> CarRepository { get; }
        ICRUDRepository<ParkingSpace> ParkingSpaceRepository { get; }
        ICRUDRepository<ParkingSpaceCar> ParkingSpaceCarRepository { get; }
        ICRUDRepository<TempOwnerParkingSpace> TempOwnerParkingSpaceRepository { get; }

        void Save();
    }
}
