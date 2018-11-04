using AlphaParking.BLL.Services.DTO;
using AlphaParking.BLL.Services.Exceptions;
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
    public class ParkingSpaceService : IParkingSpaceService
    {
        private readonly IUnitOfWork _database;
        private readonly IMapper _mapper;
        public ParkingSpaceService(IUnitOfWork unitOfWork, IMapper mapper)
        {
            _database = unitOfWork;
            _mapper = mapper;
        }

        public async void CheckInOnMainParkingSpace(int numParkingSpace, string carNumber)
        {
            var mainParkingSpace = await _database.ParkingSpaceCarRepository
                .GetElem(x =>x.ParkingSpaceNumber == numParkingSpace && x.CarNumber == carNumber && x.IsMainParkingSpace);
            if (mainParkingSpace.CheckIn)
                throw new BadRequestException("Основное место уже занято");
            mainParkingSpace.CheckIn = true;
            _database.Save();
        }

        public async void CheckInOnOtherParkingSpace(int numParkingSpace, string carNumber)
        {
            var parkingSpace = await _database.ParkingSpaceCarRepository
                .GetElem(x => x.ParkingSpaceNumber == numParkingSpace && x.CarNumber == carNumber);
            if (parkingSpace.IsMainParkingSpace)
                throw new BadRequestException("Это основное парковочное место");
            if (parkingSpace.CheckIn)
                throw new BadRequestException("Основное место уже занято");
            parkingSpace.CheckIn = true;
            _database.Save();
        }

        public async void GiveParkingSpaceToOther(int numParkingSpace, string ownerCarNumber, 
            string otherCarNumber, DateTime startDate, DateTime endDate)
        {
            var parkingSpaceCar = await _database.ParkingSpaceCarRepository
                .GetElem(x => x.ParkingSpaceNumber == numParkingSpace 
                && x.CarNumber == ownerCarNumber);
            if (parkingSpaceCar.DelegatedCar != null)
                throw new BadRequestException("Место уже делегировано другому пользователю.");

            parkingSpaceCar.DelegatedCarNumber = otherCarNumber;
            parkingSpaceCar.StartDelegatedDate = startDate;
            parkingSpaceCar.EndDelegatedDate = endDate;

            _database.Save();
        }

        public async Task<ParkingSpaceDTO> Create(ParkingSpaceDTO parkingSpace)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<ParkingSpaceDTO, ParkingSpace>
                (_database.ParkingSpaceRepository.Create, parkingSpace, _mapper);
        }

        public async Task<ParkingSpaceDTO> Delete(ParkingSpaceDTO parkingSpace)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<ParkingSpaceDTO, ParkingSpace>
                (_database.ParkingSpaceRepository.Delete, parkingSpace, _mapper);
        }

        public async Task<ParkingSpaceDTO> Get(int numberId)
        {
            return _mapper.Map<ParkingSpaceDTO>
                (await _database.ParkingSpaceRepository.GetElem(elem => elem.Number == numberId, elem => elem.ParkingSpaceCars));
        }

        public async Task<ParkingSpaceDTO> Update(ParkingSpaceDTO parkingSpace)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<ParkingSpaceDTO, ParkingSpace>
                (_database.ParkingSpaceRepository.Update, parkingSpace, _mapper);
        }

        public async Task<IEnumerable<ParkingSpaceDTO>> GetAll()
        {
            return _mapper.Map<IEnumerable<ParkingSpace>, IEnumerable<ParkingSpaceDTO>>(await _database.ParkingSpaceRepository.GetElems());
        }

        public void Dispose()
        {
            _database.Dispose();
        }
    }
}
