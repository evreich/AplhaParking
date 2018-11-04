using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.ViewModels
{
    public class UserViewModel : IViewModel
    {
        public Guid Id { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public string FIO { get; set; }
        public string Address { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }

        public List<CarViewModel> Cars { get; set; } = new List<CarViewModel>();
        public List<RoleViewModel> Roles { get; set; } = new List<RoleViewModel>();
    }
}
