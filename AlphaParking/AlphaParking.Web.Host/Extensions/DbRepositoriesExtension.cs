﻿using AlphaParking.DAL;
using AlphaParking.Models;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.Extensions
{
    static class DbRepositoriesExtension
    {
        public static void AddDbRepositories(this IServiceCollection services)
        {
            services.AddTransient<ICRUDRepository<Car>, CRUDRepository<Car>>();
            services.AddTransient<ICRUDRepository<ParkingSpace>, CRUDRepository<ParkingSpace>>();
            services.AddTransient<ICRUDRepository<ParkingSpaceCar>, CRUDRepository<ParkingSpaceCar>>();
            services.AddTransient<ICRUDRepository<User>, CRUDRepository<User>>();
            services.AddTransient<ICRUDRepository<UserRole>, CRUDRepository<UserRole>>();
            services.AddTransient<ICRUDRepository<Role>, CRUDRepository<Role>>();
        }
    }
}
