using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.DAL.Repositories
{
    public interface ICRUDRepository<TEntity>: IDisposable where TEntity: class
    {
        Task<TEntity> Create(TEntity elem);
        Task<TEntity> Delete(TEntity elem);
        Task<TEntity> Update(TEntity elem);
        Task<TEntity> GetElem(Expression<Func<TEntity, bool>> predicate);
        Task<TEntity> GetElem(Expression<Func<TEntity, bool>> predicate, params Expression<Func<TEntity, object>>[] includeProperties);

        Task<IEnumerable<TEntity>> GetElems();
        Task<IEnumerable<TEntity>> GetElems(Expression<Func<TEntity, bool>> predicate);
        Task<IEnumerable<TEntity>> GetElems(params Expression<Func<TEntity, object>>[] includeProperties);
        Task<IEnumerable<TEntity>> GetElems(Expression<Func<TEntity, bool>> predicate,
            params Expression<Func<TEntity, object>>[] includeProperties);
    }
}
