using AlphaParking.BLL.DTO;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.ViewModels.MapperProfiles
{
    public class ParkingSpaceCarProfile: Profile
    {
        public ParkingSpaceCarProfile()
        {
            CreateMap<ParkingSpaceCarViewModel, ParkingSpaceCarDTO>()
                .ReverseMap();
        }
    }
}
