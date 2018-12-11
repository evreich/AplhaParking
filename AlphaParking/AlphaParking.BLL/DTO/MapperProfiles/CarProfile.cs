using AlphaParking.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO.MapperProfiles
{
    public class CarProfile: Profile
    {
        public CarProfile()
        {
            CreateMap<Car, CarDTO>()
                .ReverseMap()
                .ForPath(car => car.User, opt => opt.Ignore());
        }
    }
}
