using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.ViewModels
{
    public class RoleViewModel : IViewModel
    {
        public Guid Id { get; set; }
        public string Name { get; set; }

        public List<UserViewModel> Users { get; set; } = new List<UserViewModel>();
    }
}
