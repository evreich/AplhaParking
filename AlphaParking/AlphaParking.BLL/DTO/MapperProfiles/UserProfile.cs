using AlphaParking.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO.MapperProfiles
{
    public class UserProfile: Profile
    {
        public UserProfile()
        {
            CreateMap<User, UserDTO>()
                .ForMember(dto => dto.Cars, opt => opt.MapFrom(u => u.UserCars))
                .ReverseMap()
                .ForPath(u => u.UserCars, opt => opt.MapFrom(dto => dto.Cars));
        }
    }
}
