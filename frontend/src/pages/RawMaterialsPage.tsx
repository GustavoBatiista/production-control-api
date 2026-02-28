import { useEffect, useState } from 'react';
import { getRawMaterials, createRawMaterial, updateRawMaterial, deleteRawMaterial } from '../services/api';
import { getErrorMessage } from '../utils/errorMessage';
import type { RawMaterial } from '../types';
import { TableContainer } from '../components/TableContainer';

export function RawMaterialsPage() {
  const [rawMaterials, setRawMaterials] = useState<RawMaterial[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [editing, setEditing] = useState<RawMaterial | null>(null);
  const [formOpen, setFormOpen] = useState(false);
  const [form, setForm] = useState({ code: '', name: '', stockQuantity: '' });

  const loadRawMaterials = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await getRawMaterials();
      setRawMaterials(res.data);
    } catch (e) {
      setError(getErrorMessage(e));
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadRawMaterials();
  }, []);

  const openCreate = () => {
    setEditing(null);
    setForm({ code: '', name: '', stockQuantity: '' });
    setFormOpen(true);
    setError(null);
  };

  const openEdit = (r: RawMaterial) => {
    setEditing(r);
    setForm({ code: r.code, name: r.name, stockQuantity: String(r.stockQuantity) });
    setFormOpen(true);
    setError(null);
  };

  const closeForm = () => {
    setEditing(null);
    setForm({ code: '', name: '', stockQuantity: '' });
    setFormOpen(false);
    setError(null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const stockQuantity = parseInt(form.stockQuantity, 10);
    if (isNaN(stockQuantity) || stockQuantity < 0) {
      setError('Please enter a valid stock quantity.');
      return;
    }
    setError(null);
    try {
      if (editing) {
        await updateRawMaterial(editing.id, { code: form.code, name: form.name, stockQuantity });
      } else {
        await createRawMaterial({ code: form.code, name: form.name, stockQuantity });
      }
      loadRawMaterials();
      closeForm();
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Delete this raw material?')) return;
    setError(null);
    try {
      await deleteRawMaterial(id);
      loadRawMaterials();
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  return (
    <div>
      <h1 className="page-title">Raw Materials</h1>
      <button className="btn" onClick={openCreate}>New Raw Material</button>
      {error && <div className="error-msg">{error}</div>}
      {formOpen && (
        <form className="form-inline" onSubmit={handleSubmit}>
          <input placeholder="Code" value={form.code} onChange={(e) => setForm({ ...form, code: e.target.value })} required />
          <input placeholder="Name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
          <input type="number" placeholder="Stock Quantity" value={form.stockQuantity} onChange={(e) => setForm({ ...form, stockQuantity: e.target.value })} required />
          <button type="submit" className="btn">{editing ? 'Update' : 'Create'}</button>
          <button type="button" className="btn btn-secondary" onClick={closeForm}>Cancel</button>
        </form>
      )}
      {loading ? (
        <p className="loading">Loading...</p>
      ) : (
        <TableContainer>
        <table className="data-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Code</th>
              <th>Name</th>
              <th>Stock Quantity</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {rawMaterials.map((r) => (
              <tr key={r.id}>
                <td>{r.id}</td>
                <td>{r.code}</td>
                <td>{r.name}</td>
                <td>{r.stockQuantity}</td>
                <td>
                  <button className="btn btn-secondary btn-small" onClick={() => openEdit(r)}>Edit</button>
                  <button className="btn btn-danger btn-small" onClick={() => handleDelete(r.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        </TableContainer>
      )}
    </div>
  );
}