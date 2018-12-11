using AlphaParking.Models;
using System;

namespace AlphaParking.DAL
{
    public interface IUnitOfWork: IDisposable
    {
        ICRUDRepository<User> UserRepository { get; }
        ICRUDRepository<Car> CarRepository { get; }
        ICRUDRepository<ParkingSpace> ParkingSpaceRepository { get; }
        ICRUDRepository<ParkingSpaceCar> ParkingSpaceCarRepository { get; }

        void Save();
        void TryToApplyMigration();
    }
}
