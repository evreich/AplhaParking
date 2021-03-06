﻿// <auto-generated />
using System;
using AlphaParking.DbContext.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

namespace AlphaParking.Models.Migrations
{
    [DbContext(typeof(AlphaParkingDbContext))]
    partial class AlphaParkingDbContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.SerialColumn)
                .HasAnnotation("ProductVersion", "2.1.3-rtm-32065")
                .HasAnnotation("Relational:MaxIdentifierLength", 63);

            modelBuilder.Entity("AlphaParking.Models.Car", b =>
                {
                    b.Property<string>("Number")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Brand");

                    b.Property<string>("Model");

                    b.Property<int>("UserId");

                    b.HasKey("Number");

                    b.HasIndex("UserId");

                    b.ToTable("Cars");
                });

            modelBuilder.Entity("AlphaParking.Models.ParkingSpace", b =>
                {
                    b.Property<int>("Number");

                    b.HasKey("Number");

                    b.ToTable("ParkingSpaces");
                });

            modelBuilder.Entity("AlphaParking.Models.ParkingSpaceCar", b =>
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

            modelBuilder.Entity("AlphaParking.Models.User", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("FIO");

                    b.Property<string>("Login");

                    b.Property<string>("Phone");

                    b.HasKey("Id");

                    b.HasIndex("Login")
                        .IsUnique();

                    b.ToTable("Users");
                });

            modelBuilder.Entity("AlphaParking.Models.Car", b =>
                {
                    b.HasOne("AlphaParking.Models.User", "User")
                        .WithMany("UserCars")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("AlphaParking.Models.ParkingSpaceCar", b =>
                {
                    b.HasOne("AlphaParking.Models.Car", "Car")
                        .WithMany("ParkingSpaceCars")
                        .HasForeignKey("CarNumber");

                    b.HasOne("AlphaParking.Models.Car", "DelegatedCar")
                        .WithMany()
                        .HasForeignKey("DelegatedCarNumber");

                    b.HasOne("AlphaParking.Models.ParkingSpace", "ParkingSpace")
                        .WithMany("ParkingSpaceCars")
                        .HasForeignKey("ParkingSpaceNumber")
                        .OnDelete(DeleteBehavior.Cascade);
                });
#pragma warning restore 612, 618
        }
    }
}
