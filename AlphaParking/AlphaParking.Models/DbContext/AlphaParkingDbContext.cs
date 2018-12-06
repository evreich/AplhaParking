using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.EntityFrameworkCore.Proxies;
using AlphaParking.Models;
using AlphaParking.Models.SeedData;

namespace AlphaParking.DbContext.Models
{
    public class AlphaParkingDbContext : Microsoft.EntityFrameworkCore.DbContext
    {
        public AlphaParkingDbContext(DbContextOptions<AlphaParkingDbContext> options ): base(options) { }

        public DbSet<Car> Cars { get; set; }
        public DbSet<ParkingSpaceCar> ParkingSpaceCars {get; set;}
        public DbSet<ParkingSpace> ParkingSpaces { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<UserRole> UserRoles { get; set; }
        public DbSet<Role> Roles { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //multi keys
            modelBuilder.Entity<UserRole>()
                .HasKey(ur => new { ur.RoleId, ur.UserId });
            
            //many-to-many
            modelBuilder.Entity<UserRole>()
                .HasOne(elem => elem.Role)
                .WithMany(elem => elem.UserRoles)
                .HasForeignKey(elem => elem.RoleId);

            modelBuilder.Entity<UserRole>()
                .HasOne(elem => elem.User)
                .WithMany(elem => elem.UserRoles)
                .HasForeignKey(elem => elem.UserId);

            modelBuilder.Entity<ParkingSpaceCar>()
                .HasOne(elem => elem.Car)
                .WithMany(elem => elem.ParkingSpaceCars)
                .HasForeignKey(elem => elem.CarNumber);

            modelBuilder.Entity<ParkingSpaceCar>()
                .HasOne(elem => elem.ParkingSpace)
                .WithMany(elem => elem.ParkingSpaceCars)
                .HasForeignKey(elem => elem.ParkingSpaceNumber);

            //init start values
            modelBuilder.Entity<Role>().HasData(
                new Role { Id = RoleConstants.Employee.Id, Name = RoleConstants.Employee.Name },
                new Role { Id = RoleConstants.Manager.Id, Name = RoleConstants.Manager.Name }
            );

            base.OnModelCreating(modelBuilder);
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            //optionsBuilder.UseLazyLoadingProxies();
        }

    }


}
