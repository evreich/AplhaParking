using AlphaParking.BLL.Interfaces;
using AlphaParking.DAL.Interfaces;
using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services
{
    public class CarService : ICarService
    {
        private readonly IUnitOfWork _unitOfWork;
        public CarService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<Car> Create(Car elem)
        {
            return await _unitOfWork.CarRepository.Create(elem);
        }

        public async Task<Car> Delete(Car elem)
        {
            return await _unitOfWork.CarRepository.Delete(elem);
        }

        public async Task<IEnumerable<Car>> GetAll()
        {
            return await _unitOfWork.CarRepository.GetElems(elem => elem.UserCars);
        }

        public async Task<Car> Update(Car elem)
        {
            return await _unitOfWork.CarRepository.Update(elem);
        }

        public void Dispose()
        {
            _unitOfWork.Dispose();
        }
    }
}
