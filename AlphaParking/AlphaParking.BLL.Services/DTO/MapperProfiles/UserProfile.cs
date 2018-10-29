using AlphaParking.DB.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Services.DTO.MapperProfiles
{
    public class UserProfile: Profile
    {
        public UserProfile()
        {
            CreateMap<User, UserDTO>()
                .ForMember(dto => dto.Cars, opt => opt.MapFrom(u => u.UserCars))
                .ForMember(dto => dto.Roles, opt => opt.MapFrom(u => u.UserRoles))
                .ReverseMap()
                .ForPath(u => u.UserCars, opt => opt.MapFrom(dto => dto.Cars))
                .ForPath(u => u.UserRoles, opt => opt.MapFrom(dto => dto.Roles));
        }
    }
}
