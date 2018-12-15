using AlphaParking.BLL.DTO.MapperProfiles;
using AutoMapper;
using Microsoft.Extensions.DependencyInjection;
using System.Reflection;

namespace AlphaParking.Web.Host.Extensions
{
    static class AutoMapperExtension
    {
        public static void AddConfiguratedAutoMapper(this IServiceCollection services)
        {
            services.AddAutoMapper
            (
                typeof(UserProfile).GetTypeInfo().Assembly, 
                typeof(CarProfile).GetTypeInfo().Assembly, 
                typeof(ParkingSpaceProfile).GetTypeInfo().Assembly, 
                typeof(ParkingSpaceCarProfile).GetTypeInfo().Assembly, 
                typeof(ViewModels.MapperProfiles.CarProfile).GetTypeInfo().Assembly, 
                typeof(ViewModels.MapperProfiles.UserProfile).GetTypeInfo().Assembly,
                typeof(ViewModels.MapperProfiles.ParkingSpaceCarProfile).GetTypeInfo().Assembly, 
                typeof(ViewModels.MapperProfiles.ParkingSpaceProfile).GetTypeInfo().Assembly
            );
        }
    }
}