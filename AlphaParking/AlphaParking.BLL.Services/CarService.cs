﻿using AlphaParking.BLL.Services.DTO;
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
    public class CarService : ICarService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IMapper _mapper;

        public CarService(IUnitOfWork unitOfWork, IMapper mapper)
        {
            _unitOfWork = unitOfWork;
            _mapper = mapper;
        }

        public async Task<CarDTO> Create(CarDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<CarDTO,Car>(_unitOfWork.CarRepository.Create, elem, _mapper);
        }

        public async Task<CarDTO> Delete(CarDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<CarDTO, Car>(_unitOfWork.CarRepository.Delete, elem, _mapper);
        }

        public async Task<IEnumerable<CarDTO>> GetAll()
        {
            return _mapper.Map<IEnumerable<Car>, IEnumerable<CarDTO>>(await _unitOfWork.CarRepository.GetElems());
        }

        public async Task<CarDTO> Update(CarDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<CarDTO,Car>(_unitOfWork.CarRepository.Update, elem, _mapper);
        }

        public void Dispose()
        {
            _unitOfWork.Dispose();
        }
    }
}
