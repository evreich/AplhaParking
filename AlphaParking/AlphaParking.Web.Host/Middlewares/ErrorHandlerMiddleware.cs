using AlphaParking.BLL.Exceptions;
using AlphaParking.Web.Host.Utils;
using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace AlphaParking.Web.Host.Middlewares
{
    public class ErrorHandlerMiddleware
    {
        private readonly RequestDelegate _next;

        public ErrorHandlerMiddleware(RequestDelegate next)
        {
            _next = next ?? throw new ArgumentNullException(nameof(next));
        }

        public async Task Invoke(HttpContext context)
        {
            try
            {
                await _next(context);
            }
            catch(ValidationException ex)
            {
                await BuildErrorResponse(context, ex, StatusCodes.Status400BadRequest);
            }
            catch(BadRequestException ex)
            {
                await BuildErrorResponse(context, ex, StatusCodes.Status400BadRequest);
            }
            catch(ForbiddenException ex)
            {
                await BuildErrorResponse(context, ex, StatusCodes.Status403Forbidden);
            }
            catch (Exception ex)
            {
                await BuildErrorResponse(context, ex, StatusCodes.Status500InternalServerError);
            }
        }

        private async Task BuildErrorResponse(HttpContext context, Exception ex, int statusCode)
        {
            context.Response.Body = new MemoryStream();
            context.Response.StatusCode = statusCode;
            context.Response.ContentType = "application/json";
            var responseText = JsonConvert.SerializeObject(new ResponseError(ex.Message));
            await context.Response.WriteAsync(responseText);
        }
    }
}
