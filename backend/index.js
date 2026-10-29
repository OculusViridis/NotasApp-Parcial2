const express = require('express');
const { Pool } = require('pg');
const jwt = require('jsonwebtoken');
const cors = require('cors');

const app = express();
app.use(express.json());
app.use(cors());

const SECRET_KEY = 'udv_secreto_super_seguro_2026';

// Conexión al contenedor Docker postgres-parcial2
const pool = new Pool({
    user: 'postgres',
    host: 'localhost',
    database: 'notas_db',
    password: 'admin',
    port: 5432,
});

// Middleware para validar el token JWT (Requisito 5.c)
const verificarToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    if (!authHeader) {
        return res.status(401).json({ exito: false, mensaje: 'Token no proporcionado' });
    }
    const token = authHeader.split(' ')[1];
    if (!token) {
        return res.status(401).json({ exito: false, mensaje: 'Formato de token inválido' });
    }

    jwt.verify(token, SECRET_KEY, (err, decoded) => {
        if (err) {
            return res.status(403).json({ exito: false, mensaje: 'Token inválido o expirado' });
        }
        req.usuarioId = decoded.id;
        next();
    });
};

// 1. Endpoint de Login
app.post('/api/login', async (req, res) => {
    const { email, password } = req.body;
    try {
        const result = await pool.query(
            'SELECT id, nombre, email FROM usuarios WHERE email = $1 AND password = $2',
            [email, password]
        );
        if (result.rows.length === 0) {
            return res.status(401).json({ exito: false, mensaje: 'Credenciales incorrectas' });
        }
        const usuario = result.rows[0];
        const token = jwt.sign({ id: usuario.id, email: usuario.email }, SECRET_KEY, { expiresIn: '24h' });

        res.json({
            exito: true,
            mensaje: 'Autenticación exitosa',
            token: token,
            nombre: usuario.nombre,
            email: usuario.email
        });
    } catch (error) {
        res.status(500).json({ exito: false, mensaje: 'Error en el servidor: ' + error.message });
    }
});

// 2. Endpoint para Crear Nota (Requisito 5.a)
app.post('/api/notas', verificarToken, async (req, res) => {
    const { titulo, contenido } = req.body;
    if (!titulo || !contenido) {
        return res.status(400).json({ exito: false, mensaje: 'Título y contenido son obligatorios' });
    }
    try {
        const result = await pool.query(
            'INSERT INTO notas (usuario_id, titulo, contenido) VALUES ($1, $2, $3) RETURNING id, usuario_id, titulo, contenido, fecha_creacion',
            [req.usuarioId, titulo, contenido]
        );
        res.status(201).json({
            exito: true,
            mensaje: 'Nota guardada exitosamente',
            nota: result.rows[0]
        });
    } catch (error) {
        res.status(500).json({ exito: false, mensaje: 'Error al guardar la nota: ' + error.message });
    }
});

// 3. Endpoint para Obtener Notas del Usuario (Requisito 5.b)
app.get('/api/notas', verificarToken, async (req, res) => {
    try {
        const result = await pool.query(
            'SELECT id, titulo, contenido, fecha_creacion FROM notas WHERE usuario_id = $1 ORDER BY fecha_creacion DESC',
            [req.usuarioId]
        );
        res.json(result.rows);
    } catch (error) {
        res.status(500).json({ exito: false, mensaje: 'Error al obtener las notas: ' + error.message });
    }
});

app.listen(3000, '0.0.0.0', () => {
    console.log('Servidor Backend ejecutándose en el puerto 3000');
});