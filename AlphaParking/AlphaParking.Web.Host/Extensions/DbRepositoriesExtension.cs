using AlphaParking.DAL;
using AlphaParking.DAL.UnitOfWork;
using AlphaParking.DbContext.Models;
using AlphaParking.Models;
using Microsoft.EntityFrameworkCore;
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

        public static void AddDbContextFactory(this IServiceCollection services, string defaultConnection)
        {
            services.AddSingleton<Func<AlphaParkingDbContext>>(() =>
            {
                var optionsBuilder = new DbContextOptionsBuilder<AlphaParkingDbContext>();
                optionsBuilder.UseNpgsql(defaultConnection);
                return new AlphaParkingDbContext(optionsBuilder.Options);
            });
        }
    }
}
