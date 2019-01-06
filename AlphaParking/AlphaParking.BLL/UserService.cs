using AlphaParking.Models;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using AlphaParking.DAL;
using AutoMapper;
using AlphaParking.BLL.DTO;
using AlphaParking.BLL.Utils;

namespace AlphaParking.BLL
{
    public class UserService : IUserService
    {
        private readonly IUnitOfWork _database;
        private readonly IMapper _mapper;

        public UserService(IUnitOfWork unitOfWork, IMapper mapper)
        {
            _database = unitOfWork;
            _mapper = mapper;
        }

        private User MapUserFromDTO(UserDTO elem)
        {
            var user = _mapper.Map<User>(elem);
            return user;
        }

        public async Task<IEnumerable<UserDTO>> GetAll()
        {
            return _mapper.Map<IEnumerable<User>, IEnumerable<UserDTO>>
                (await _database.UserRepository.GetElems(elem => elem.UserCars));
        }

        public async Task<UserDTO> GetUser(int userId)
        {
            return _mapper.Map<UserDTO>
                (await _database.UserRepository.GetElem(elem => elem.Id == userId, elem => elem.UserCars));
        }

        public async Task<IEnumerable<ParkingSpaceDTO>> GetUserParkingPlaces(int userId)
        {
            var cars = _mapper.Map<UserDTO>((await _database.UserRepository.GetElem(elem => elem.Id == userId, elem => elem.UserCars))).Cars;
            return _mapper.Map<IEnumerable<ParkingSpace>,IEnumerable<ParkingSpaceDTO>>
                ((await _database.ParkingSpaceCarRepository
                .GetElems(elem => cars.Any(uc => uc.Number == elem.CarNumber || uc.Number == elem.DelegatedCarNumber)))
                .Select(elem => elem.ParkingSpace));
        }

        public void Dispose()
        {
            _database.Dispose();
        }

        public async Task<IEnumerable<CarDTO>> GetUserCars(int userId)
        {
            return _mapper.Map<IEnumerable<Car>,IEnumerable<CarDTO>>(
                await _database.CarRepository.GetElems(
                    car => car.UserId == userId, 
                    car => car.User)
            );
        }
        public async Task<UserDTO> Create(UserDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<UserDTO, User>
                (_database.UserRepository.Create, elem, _mapper);
        }

        public async Task<UserDTO> Update(UserDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<UserDTO, User>
                (_database.UserRepository.Update, elem, _mapper);
        }

        public async Task<UserDTO> Delete(UserDTO elem)
        {
            return await MappingDataUtils.WrapperMappingDALFunc<UserDTO, User>
                (_database.UserRepository.Delete, elem, _mapper);
        }
    }
}
