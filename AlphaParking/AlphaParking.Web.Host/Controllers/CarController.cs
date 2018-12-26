using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using AlphaParking.BLL;
using AlphaParking.BLL.DTO;
using AlphaParking.BLL.Exceptions;
using AlphaParking.Web.Host.Utils;
using AlphaParking.Web.Host.ViewModels;
using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace AlphaParking.Web.Host.Controllers
{
    [Route("[controller]")]
    [ApiController]
    [Authorize(Roles = "employee")]
    public class CarController : ControllerBase
    {
        private readonly ICarService _carService;
        private readonly IMapper _mapper;

        public CarController(ICarService carService, IMapper mapper)
        {
            _carService = carService;
            _mapper = mapper;
        }

        [HttpPost]        
        public async Task<IActionResult> Create([FromBody] CarViewModel car)
        {
            ValidationUtils.CheckViewModelNotIsNull(car);
            ModelState.CheckModelStateValidation();

            var carDTO = _mapper.Map<CarViewModel, CarDTO>(car);
            carDTO = await _carService.Create(carDTO);
            var createdCar = _mapper.Map<CarDTO, CarViewModel>(carDTO);

            var uri = new Uri($"{HttpContext.Request.Host}{HttpContext.Request.Path.Value}");
            return Created(uri, createdCar);
        }

        [HttpPut]
        public async Task<IActionResult> Update([FromBody] CarViewModel car)
        {
            ValidationUtils.CheckViewModelNotIsNull(car);
            ModelState.CheckModelStateValidation();

            var carDTO = _mapper.Map<CarViewModel, CarDTO>(car);
            carDTO = await _carService.Update(carDTO);
            var editedCar = _mapper.Map<CarDTO, CarViewModel>(carDTO);

            return Ok(editedCar);
        }

        [HttpDelete("{carNumber}")]
        public async Task<IActionResult> Delete(string carNumber)
        {
            if (carNumber == null)
            {
                throw new BadRequestException("Получено пустое значение.");
            }
            var deletedCar = _mapper.Map<CarDTO, CarViewModel>(await _carService.Delete(new CarDTO { Number = carNumber }));
            return Ok(deletedCar);
        }

        [HttpGet]
        public async Task<IActionResult> GetAll()
        {
            return Ok(_mapper.Map<IEnumerable<CarDTO>, IEnumerable<CarViewModel>>(await _carService.GetAll()));
        }
    }
}