using AlphaParking.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO.MapperProfiles
{
    public class RoleProfile: Profile
    {
        public RoleProfile()
        {
            CreateMap<Role, RoleDTO>()
                .ForMember(dto => dto.Users, opt => opt.MapFrom(u => u.UserRoles))
                .ReverseMap()
                .ForPath(u => u.UserRoles, opt => opt.MapFrom(dto => dto.Users));
        }
    }
}
