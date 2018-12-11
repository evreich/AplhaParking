using Microsoft.Extensions.DependencyInjection;
using AlphaParking.DAL;
using AlphaParking.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AlphaParking.BLL;
using AutoMapper;

namespace AlphaParking.Web.Host.Extensions
{
    static class ServicesExtension
    {
        public static void AddServices(this IServiceCollection services)
        {
            services.AddScoped<ICarService, CarService>();
            services.AddScoped<IParkingSpaceService, ParkingSpaceService>();
            services.AddScoped<IUserService, UserService>();
        }
    }
}
