using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.Utils
{
    public struct ResponseError
    {
        public string errorMessage;

        public ResponseError(string errorMsg)
        {
            errorMessage = errorMsg;
        }
    }
}
