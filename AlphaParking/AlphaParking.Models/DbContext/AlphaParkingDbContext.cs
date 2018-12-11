using Microsoft.EntityFrameworkCore;
using AlphaParking.Models;

namespace AlphaParking.DbContext.Models
{
    public class AlphaParkingDbContext : Microsoft.EntityFrameworkCore.DbContext
    {
        public AlphaParkingDbContext(DbContextOptions<AlphaParkingDbContext> options ): base(options) { }

        public DbSet<Car> Cars { get; set; }
        public DbSet<ParkingSpaceCar> ParkingSpaceCars {get; set;}
        public DbSet<ParkingSpace> ParkingSpaces { get; set; }
        public DbSet<User> Users { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<ParkingSpaceCar>()
                .HasOne(elem => elem.Car)
                .WithMany(elem => elem.ParkingSpaceCars)
                .HasForeignKey(elem => elem.CarNumber);

            modelBuilder.Entity<ParkingSpaceCar>()
                .HasOne(elem => elem.ParkingSpace)
                .WithMany(elem => elem.ParkingSpaceCars)
                .HasForeignKey(elem => elem.ParkingSpaceNumber);

            base.OnModelCreating(modelBuilder);
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
        //optionsBuilder.UseLazyLoadingProxies();
        }

    }


}
