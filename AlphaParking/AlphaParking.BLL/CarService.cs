using AlphaParking.BLL.DTO;
using AlphaParking.BLL.Utils;
using AlphaParking.DAL;
using AlphaParking.Models;
using AutoMapper;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL
{
    public class CarService : ICarService
    {
        private readonly IUnitOfWork _database;
        private readonly IMapper _mapper;

        public CarService(IUnitOfWork unitOfWork, IMapper mapper)
        {
            _database = unitOfWork;
            _mapper = mapper;
        }

        public async Task<CarDTO> Create(CarDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<CarDTO,Car>(_database.CarRepository.Create, elem, _mapper);
        }

        public async Task<CarDTO> Delete(CarDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<CarDTO, Car>(_database.CarRepository.Delete, elem, _mapper);
        }

        public async Task<IEnumerable<CarDTO>> GetAll()
        {
            return _mapper.Map<IEnumerable<Car>, IEnumerable<CarDTO>>(await _database.CarRepository.GetElems());
        }

        public async Task<CarDTO> Update(CarDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<CarDTO,Car>(_database.CarRepository.Update, elem, _mapper);
        }

        public void Dispose()
        {
            _database.Dispose();
        }
    }
}
