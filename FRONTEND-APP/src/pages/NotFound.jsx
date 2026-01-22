export default function NotFound(){
    return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
            <h1 className="font-bold text-6xl mb-2">404 - Página No Encontrada</h1>
            <p className="font-light text-2xl">Lo sentimos, la página que buscas no existe. Vuelva mas tarde</p>
            <img 
                src="https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExanVwejd5dW50MzMzeGU5YzA2czc5M2c1NTY5Z2xjZXN3M3E2Ym9zdyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/14uQ3cOFteDaU/giphy.gif" 
                alt="Página en desarrollo" 
                className="m-auto rounded-2xl pt-2"
            />
        </div>
    );
}