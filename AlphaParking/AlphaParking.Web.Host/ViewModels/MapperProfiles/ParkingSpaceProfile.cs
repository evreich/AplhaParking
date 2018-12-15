using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using AlphaParking.BLL.DTO;

namespace AlphaParking.Web.Host.ViewModels.MapperProfiles
{
    public class ParkingSpaceProfile : Profile
    {
        public ParkingSpaceProfile()
        {
            CreateMap<ParkingSpaceViewModel, ParkingSpaceDTO>()
              .ReverseMap();
        }
    }
}
