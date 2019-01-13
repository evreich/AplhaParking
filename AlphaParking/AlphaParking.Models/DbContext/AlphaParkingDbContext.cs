using Microsoft.EntityFrameworkCore;
using AlphaParking.Models;
using Microsoft.EntityFrameworkCore.Design;

namespace AlphaParking.DbContext.Models
{
    public class ApplicationContextDbFactory : IDesignTimeDbContextFactory<AlphaParkingDbContext>
    {
        public AlphaParkingDbContext CreateDbContext(string[] args)
        {
            var optionsBuilder = new DbContextOptionsBuilder<AlphaParkingDbContext>();
            const string DbUpdateConcurrencyException = "Server=mainparkingdb.postgres.database.azure.com;Database=postgres;Port=5432;User Id=evreich@mainparkingdb;Password=HeLeNnIkAcAt123";

            optionsBuilder.UseNpgsql<AlphaParkingDbContext>(DbUpdateConcurrencyException);

            return new AlphaParkingDbContext(optionsBuilder.Options);
        }
    }
    public class AlphaParkingDbContext : Microsoft.EntityFrameworkCore.DbContext
    {
        public AlphaParkingDbContext(DbContextOptions<AlphaParkingDbContext> options) : base(options) { }

        public DbSet<Car> Cars { get; set; }
        public DbSet<ParkingSpaceCar> ParkingSpaceCars { get; set; }
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
