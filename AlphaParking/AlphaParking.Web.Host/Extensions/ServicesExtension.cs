using Microsoft.Extensions.DependencyInjection;
using AlphaParking.DAL.Repositories;
using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AlphaParking.BLL.Services;
using AutoMapper;

namespace AlphaParking.Web.Host.Extensions
{
    static class ServicesExtension
    {
        public static void AddServices(this IServiceCollection services, string jwtAudience)
        {
            services.AddTransient<ICarService, CarService>();
            services.AddTransient<IParkingSpaceService, ParkingSpaceService>();
            services.AddTransient<IRoleService, RoleService>();
            services.AddTransient<IUserService, UserService>();
            services.AddSingleton<ISeedDbService, SeedDbService>();
            services.AddTransient<IAuthService, AuthService>(s => new AuthService
            (
                s.GetService<IUnitOfWork>(),
                s.GetService<IUserService>(),
                jwtAudience,
                s.GetService<IMapper>()
            ));
        }
    }
}
