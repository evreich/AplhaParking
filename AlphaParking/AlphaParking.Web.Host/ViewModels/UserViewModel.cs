using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.ViewModels
{
    public class UserViewModel : IViewModel
    {
        public int Id { get; set; }
        public string FIO { get; set; }
        public string Phone { get; set; }

        public List<CarViewModel> Cars { get; set; } = new List<CarViewModel>();
    }
}
