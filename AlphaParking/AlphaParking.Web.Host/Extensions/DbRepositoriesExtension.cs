using AlphaParking.DAL;
using AlphaParking.DAL.UnitOfWork;
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
            services.AddScoped<IUnitOfWork, UnitOfWork>();
            services.AddScoped<ICRUDRepository<Car>, CRUDRepository<Car>>();
            services.AddScoped<ICRUDRepository<ParkingSpace>, CRUDRepository<ParkingSpace>>();
            services.AddScoped<ICRUDRepository<ParkingSpaceCar>, CRUDRepository<ParkingSpaceCar>>();
            services.AddScoped<ICRUDRepository<User>, CRUDRepository<User>>();
        }
    }
}
