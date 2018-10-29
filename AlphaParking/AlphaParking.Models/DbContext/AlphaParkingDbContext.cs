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

            //nullable one-to-many fileds
            modelBuilder.Entity<ParkingSpaceCar>().HasOne(pc => pc.DelegatedCar)
                .WithMany(c => c.ParkingSpaceCars)
                .HasForeignKey(pc => pc.DelegatedCarNumber)
                .IsRequired(false);

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
