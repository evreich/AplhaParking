using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.DAL.Repositories
{
    public interface IUnitOfWork: IDisposable
    {
        ICRUDRepository<Role> RoleRepository { get; }
        ICRUDRepository<UserRole> UserRoleRepository { get; }
        ICRUDRepository<User> UserRepository { get; }
        ICRUDRepository<Car> CarRepository { get; }
        ICRUDRepository<ParkingSpace> ParkingSpaceRepository { get; }
        ICRUDRepository<ParkingSpaceCar> ParkingSpaceCarRepository { get; }

        void Save();
        void TryToApplyMigration();
    }
}
