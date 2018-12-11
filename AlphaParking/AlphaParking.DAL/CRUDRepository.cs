using AlphaParking.DbContext.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace AlphaParking.DAL
{
    public class CRUDRepository<TEntity> : ICRUDRepository<TEntity> where TEntity : class
    {
        protected readonly AlphaParkingDbContext _dbContext;
        protected readonly DbSet<TEntity> _dbSet;

        public CRUDRepository(AlphaParkingDbContext dbContext)
        {
            _dbContext = dbContext;
            _dbSet = dbContext.Set<TEntity>();
        }

        public virtual async Task<TEntity> Create(TEntity elem)
        {
            var addedElem = await _dbSet.AddAsync(elem);
            await _dbContext.SaveChangesAsync();
            return addedElem.Entity;
        }

        public virtual async Task<TEntity> Delete(TEntity elem)
        {
            var deletedElem = _dbSet.Remove(elem);
            await _dbContext.SaveChangesAsync();
            return deletedElem.Entity;
        }

        public virtual async Task<TEntity> Update(TEntity elem)
        {
            var updatedElem = _dbSet.Update(elem);
            await _dbContext.SaveChangesAsync();
            return updatedElem.Entity;
        }

        public virtual Task<TEntity> GetElem(Expression<Func<TEntity, bool>> predicate)
        {
            return _dbSet.SingleOrDefaultAsync(predicate);
        }

        public virtual async Task<TEntity> GetElem(Expression<Func<TEntity, bool>> predicate, params Expression<Func<TEntity, object>>[] includeProperties)
        {
            return await Include(includeProperties).SingleOrDefaultAsync(predicate);
        }

        public virtual async Task<IEnumerable<TEntity>> GetElems()
        {
            return await _dbSet.ToListAsync();
        }

        public virtual async Task<IEnumerable<TEntity>> GetElems(Expression<Func<TEntity, bool>> predicate)
        {
            return await _dbSet.Where(predicate).ToListAsync();
        }

        public virtual async Task<IEnumerable<TEntity>> GetElems(params Expression<Func<TEntity, object>>[] includeProperties)
        {
            return await Include(includeProperties).ToListAsync();
        }

        public virtual async Task<IEnumerable<TEntity>> GetElems(Expression<Func<TEntity, bool>> predicate, params Expression<Func<TEntity, object>>[] includeProperties)
        {
            return await Include(includeProperties)
                .Where(predicate)
                .ToListAsync();
        }

        protected IQueryable<TEntity> Include(params Expression<Func<TEntity, object>>[] includeProperties)
        {
            IQueryable<TEntity> query = _dbSet.AsNoTracking();
            return includeProperties
                .Aggregate(query, (current, includeProperty) => current.Include(includeProperty));
        }

        public void Dispose()
        {
            _dbContext.Dispose();
        }

    }
}
