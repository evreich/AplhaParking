using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.ViewModels
{
    public class CarViewModel: IViewModel
    {
        public string Number { get; set; }
        public string Brand { get; set; }
        public string Model { get; set; }

        public int UserId { get; set; }
    }
}
