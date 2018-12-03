﻿// <auto-generated />
using System;
using AlphaParking.DB.DbContext.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace AlphaParking.DB.Models.Migrations
{
    [DbContext(typeof(AlphaParkingDbContext))]
    partial class AlphaParkingDbContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.1.4-rtm-31024")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("AlphaParking.DB.Models.Car", b =>
                {
                    b.Property<string>("Number")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Brand");

                    b.Property<string>("Model");

                    b.Property<Guid>("UserId");

                    b.HasKey("Number");

                    b.HasIndex("UserId");

                    b.ToTable("Cars");
                });

            modelBuilder.Entity("AlphaParking.DB.Models.ParkingSpace", b =>
                {
                    b.Property<int>("Number");

                    b.HasKey("Number");

                    b.ToTable("ParkingSpaces");
                });

            modelBuilder.Entity("AlphaParking.DB.Models.ParkingSpaceCar", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("CarNumber");

                    b.Property<bool>("CheckIn");

                    b.Property<string>("DelegatedCarNumber");

                    b.Property<DateTime?>("EndDelegatedDate");

                    b.Property<TimeSpan>("EndParkingTime");

                    b.Property<bool>("IsMainParkingSpace");

                    b.Property<int>("ParkingSpaceNumber");

                    b.Property<DateTime?>("StartDelegatedDate");

                    b.Property<TimeSpan>("StartParkingTime");

                    b.HasKey("Id");

                    b.HasIndex("CarNumber");

                    b.HasIndex("DelegatedCarNumber");

                    b.HasIndex("ParkingSpaceNumber");

                    b.ToTable("ParkingSpaceCars");
                });

            modelBuilder.Entity("AlphaParking.DB.Models.Role", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Name");

                    b.HasKey("Id");

                    b.ToTable("Roles");

                    b.HasData(
                        new { Id = new Guid("00000002-0000-0000-0000-000000000000"), Name = "Employee" },
                        new { Id = new Guid("00000001-0000-0000-0000-000000000000"), Name = "Manager" }
                    );
                });

            modelBuilder.Entity("AlphaParking.DB.Models.User", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Address");

                    b.Property<string>("Email");

                    b.Property<string>("FIO");

                    b.Property<string>("Login");

                    b.Property<string>("Password");

                    b.Property<string>("Phone");

                    b.HasKey("Id");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("AlphaParking.DB.Models.UserRole", b =>
                {
                    b.Property<Guid>("RoleId");

                    b.Property<Guid>("UserId");

                    b.HasKey("RoleId", "UserId");

                    b.HasIndex("UserId");

                    b.ToTable("UserRoles");
                });

            modelBuilder.Entity("AlphaParking.DB.Models.Car", b =>
                {
                    b.HasOne("AlphaParking.DB.Models.User", "User")
                        .WithMany("UserCars")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("AlphaParking.DB.Models.ParkingSpaceCar", b =>
                {
                    b.HasOne("AlphaParking.DB.Models.Car", "Car")
                        .WithMany("ParkingSpaceCars")
                        .HasForeignKey("CarNumber");

                    b.HasOne("AlphaParking.DB.Models.Car", "DelegatedCar")
                        .WithMany()
                        .HasForeignKey("DelegatedCarNumber");

                    b.HasOne("AlphaParking.DB.Models.ParkingSpace", "ParkingSpace")
                        .WithMany("ParkingSpaceCars")
                        .HasForeignKey("ParkingSpaceNumber")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("AlphaParking.DB.Models.UserRole", b =>
                {
                    b.HasOne("AlphaParking.DB.Models.Role", "Role")
                        .WithMany("UserRoles")
                        .HasForeignKey("RoleId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("AlphaParking.DB.Models.User", "User")
                        .WithMany("UserRoles")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });
#pragma warning restore 612, 618
        }
    }
}