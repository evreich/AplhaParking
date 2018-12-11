using AlphaParking.DbContext.Models;
using AlphaParking.Models;
using Microsoft.EntityFrameworkCore;

namespace AlphaParking.DAL.UnitOfWork
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly AlphaParkingDbContext _dbContext;

        public UnitOfWork(AlphaParkingDbContext dbContext,ICRUDRepository<User> userRepository,
            ICRUDRepository<Car> carRepository, ICRUDRepository<ParkingSpace> parkingSpaceRepository, 
            ICRUDRepository<ParkingSpaceCar> parkingSpaceCarRepository)
        {
            _dbContext = dbContext;
            UserRepository = userRepository;
            CarRepository = carRepository;
            ParkingSpaceCarRepository = parkingSpaceCarRepository;
            ParkingSpaceRepository = parkingSpaceRepository;
        }

        public ICRUDRepository<User> UserRepository { get; }

        public ICRUDRepository<Car> CarRepository { get; }

        public ICRUDRepository<ParkingSpace> ParkingSpaceRepository { get; }

        public ICRUDRepository<ParkingSpaceCar> ParkingSpaceCarRepository { get; }

        public void Dispose()
        {
            _dbContext.Dispose();
        }

        public void Save()
        {
            _dbContext.SaveChanges();
        }

        public void TryToApplyMigration()
        {
            _dbContext.Database.Migrate();
        }
    }
}
