using AlphaParking.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO.MapperProfiles
{
    public class ParkingSpaceProfile: Profile
    {
        public ParkingSpaceProfile()
        {
            CreateMap<ParkingSpace, ParkingSpaceDTO>()
                .ReverseMap();
        }
    }
}
