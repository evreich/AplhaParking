using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.EntityFrameworkCore.Proxies;
using AlphaParking.DB.Models;

namespace AlphaParking.DB.DbContext.Models
{
    public class AlphaParkingDbContext : Microsoft.EntityFrameworkCore.DbContext
    {
        public AlphaParkingDbContext() { }

        DbSet<Car> Cars { get; set; }
        DbSet<ParkingSpaceCar> ParkingSpaceCars {get; set;}
        DbSet<ParkingSpace> ParkingSpaces { get; set; }
        DbSet<TempOwnerParkingSpace> TempOwnerParkingSpaces { get; set; }
        DbSet<UserCar> UserCars { get; set; }
        DbSet<User> Users { get; set; }
        DbSet<UserRole> UserRoles { get; set; }
        DbSet<Role> Roles { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //many-to-many для книг и заказов
            //modelBuilder.Entity<BookOrder>()
            //    .HasKey(bc => new { bc.BookId, bc.OrderId });

            //modelBuilder.Entity<BookOrder>()
            //    .HasOne(bc => bc.Book)
            //    .WithMany(b => b.BookOrders)
            //    .HasForeignKey(bc => bc.BookId);

            //modelBuilder.Entity<BookOrder>()
            //    .HasOne(bc => bc.Order)
            //    .WithMany(c => c.BookOrders)
            //    .HasForeignKey(bc => bc.OrderId);

            ////many-to-many для ролей и связанных пунктов меню
            //modelBuilder.Entity<UserRoleMenuElement>()
            //    .HasKey(user_menu => new { user_menu.UserRoleId, user_menu.MenuElementId });

            //modelBuilder.Entity<UserRoleMenuElement>()
            //    .HasOne(user_menu => user_menu.MenuElement)
            //    .WithMany(b => b.UserRoleMenuElements)
            //    .HasForeignKey(user_menu => user_menu.MenuElementId);

            //modelBuilder.Entity<UserRoleMenuElement>()
            //    .HasOne(user_menu => user_menu.UserRole)
            //    .WithMany(c => c.UserRoleMenuElements)
            //    .HasForeignKey(user_menu => user_menu.UserRoleId);

            ////many-to-many для ролей и доступных маршрутов
            //modelBuilder.Entity<UserRoleRouteElement>()
            //    .HasKey(user_role => new { user_role.UserRoleId, user_role.RouteElementId });

            //modelBuilder.Entity<UserRoleRouteElement>()
            //    .HasOne(user_role => user_role.UserRole)
            //    .WithMany(b => b.UserRoleRouteElements)
            //    .HasForeignKey(user_role => user_role.UserRoleId);

            //modelBuilder.Entity<UserRoleRouteElement>()
            //    .HasOne(user_role => user_role.RouteElement)
            //    .WithMany(c => c.UserRoleRouteElements)
            //    .HasForeignKey(user_role => user_role.RouteElementId);
            base.OnModelCreating(modelBuilder);
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseLazyLoadingProxies();
        }

    }


}
