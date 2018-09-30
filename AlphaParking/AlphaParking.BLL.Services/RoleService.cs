using AlphaParking.BLL.Interfaces;
using AlphaParking.DAL.Interfaces;
using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services
{
    public class RoleService : IRoleService
    {
        private readonly IUnitOfWork _unitOfWork;
        public RoleService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<Role> Create(Role elem)
        {
            return await _unitOfWork.RoleRepository.Create(elem);
        }

        public async Task<Role> Delete(Role elem)
        {
            return await _unitOfWork.RoleRepository.Delete(elem);
        }

        public async Task<IEnumerable<Role>> GetAll()
        {
            return await _unitOfWork.RoleRepository.GetElems(elem => elem.UserRoles);
        }

        public async Task<Role> Update(Role elem)
        {
            return await _unitOfWork.RoleRepository.Update(elem);
        }

        public void Dispose()
        {
            _unitOfWork.Dispose();
        }
    }
}
