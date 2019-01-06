using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AlphaParking.BLL;
using AlphaParking.BLL.DTO;
using AlphaParking.Web.Host.ViewModels;
using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace AlphaParking.Web.Host.Controllers
{
    [Route("[controller]")]
    [ApiController]
    [Authorize]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;
        private readonly IMapper _mapper;

        public UserController(IUserService userService, IMapper mapper)
        {
            _userService = userService;
            _mapper = mapper;
        }

        [HttpGet]
        public async Task<IActionResult> GetUsers()
        {
            return Ok(_mapper.Map<IEnumerable<UserDTO>, IEnumerable<UserViewModel>>(await _userService.GetAll()));
        }
        [HttpGet("/cars")]
        public async Task<IActionResult> GetUserCars([FromQuery] int userId)
        {
            return Ok(_mapper.Map<IEnumerable<CarDTO>, IEnumerable<CarViewModel>>(await _userService.GetUserCars(userId)));
        }
    }
}
