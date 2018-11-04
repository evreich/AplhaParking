using AlphaParking.BLL.Services.DTO;
using AlphaParking.BLL.Services.Utils;
using AlphaParking.DAL.Repositories;
using AlphaParking.DB.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services
{
    public class RoleService : IRoleService
    {
        private readonly IUnitOfWork _database;
        private readonly IMapper _mapper;

        public RoleService(IUnitOfWork unitOfWork, IMapper mapper)
        {
            _database = unitOfWork;
            _mapper = mapper;
        }

        public async Task<RoleDTO> Create(RoleDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<RoleDTO,Role>(_database.RoleRepository.Create, elem, _mapper);
        }

        public async Task<RoleDTO> Delete(RoleDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<RoleDTO, Role>(_database.RoleRepository.Delete, elem, _mapper);
        }

        public async Task<IEnumerable<RoleDTO>> GetAll()
        {
            return _mapper.Map<IEnumerable<Role>, IEnumerable<RoleDTO>>(await _database.RoleRepository.GetElems(elem => elem.UserRoles));
        }

        public async Task<RoleDTO> Update(RoleDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<RoleDTO, Role>(_database.RoleRepository.Update, elem, _mapper);
        }

        public void Dispose()
        {
            _database.Dispose();
        }
    }
}
