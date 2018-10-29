using AlphaParking.DB.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Services.DTO.MapperProfiles
{
    public class ParkingSpaceCarProfile: Profile
    {
        public ParkingSpaceCarProfile()
        {
            CreateMap<ParkingSpaceCar, ParkingSpaceCarDTO>()
                .ReverseMap();
        }
    }
}
