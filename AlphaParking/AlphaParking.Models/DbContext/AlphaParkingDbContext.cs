using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.EntityFrameworkCore.Proxies;
using AlphaParking.DB.Models;
using AlphaParking.DB.Models.SeedData;

namespace AlphaParking.DB.DbContext.Models
{
    public class AlphaParkingDbContext : Microsoft.EntityFrameworkCore.DbContext
    {
        public AlphaParkingDbContext() { }

        public DbSet<Car> Cars { get; set; }
        public DbSet<ParkingSpaceCar> ParkingSpaceCars {get; set;}
        public DbSet<ParkingSpace> ParkingSpaces { get; set; }
        public DbSet<TempOwnerParkingSpace> TempOwnerParkingSpaces { get; set; }
        public DbSet<UserCar> UserCars { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<UserRole> UserRoles { get; set; }
        public DbSet<Role> Roles { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //multi keys
            modelBuilder.Entity<UserRole>()
                .HasKey(ur => new { ur.RoleId, ur.UserId });

            modelBuilder.Entity<UserCar>()
                .HasKey(uc => new { uc.UserId, uc.CarNumber });
            
            //many-to-many
            modelBuilder.Entity<UserCar>()
                .HasOne(elem => elem.Car)
                .WithMany(car => car.UserCars)
                .HasForeignKey(elem => elem.CarNumber);

            modelBuilder.Entity<UserCar>()
                .HasOne(elem => elem.User)
                .WithMany(car => car.UserCars)
                .HasForeignKey(elem => elem.UserId);

            modelBuilder.Entity<UserRole>()
                .HasOne(elem => elem.Role)
                .WithMany(elem => elem.UserRoles)
                .HasForeignKey(elem => elem.RoleId);

            modelBuilder.Entity<UserRole>()
                .HasOne(elem => elem.User)
                .WithMany(elem => elem.UserRoles)
                .HasForeignKey(elem => elem.UserId);

            //init start values
            modelBuilder.Entity<Role>().HasData(
                new Role { Id = Guid.NewGuid(), Name = RoleConstants.EMPLOYEE },
                new Role { Id = Guid.NewGuid(), Name = RoleConstants.MANAGER }
            );

            base.OnModelCreating(modelBuilder);
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseLazyLoadingProxies();
        }

    }


}
