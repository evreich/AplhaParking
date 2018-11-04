using AlphaParking.BLL.Services.DTO;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.ViewModels.MapperProfiles
{
    public class RoleProfile: Profile
    {
        public RoleProfile()
        {
            CreateMap<RoleViewModel, RoleDTO>()
                .ReverseMap();
        }
    }
}
