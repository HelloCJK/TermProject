using UnityEngine;
using System.Collections;

[System.Serializable]
public class Boundary{
    public float xMin, xMax, yMin, yMax;
}

public class PlayerController : MonoBehaviour {

    Rigidbody rb;
    public float speed;
    public float tilt;
    public Boundary boundary = new Boundary();

    public GameObject shot;
    public Transform shotSpawn;
    public GameObject asteroid;

    public int life;

    public float fireRate;
    private float nextFire;

    AudioSource fireAudio;

    private KalmanFilter mKalmanX;
    private KalmanFilter mKalmanY;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        fireAudio = GetComponent<AudioSource>();
        mKalmanX = new KalmanFilter(Input.acceleration.x);
        mKalmanY = new KalmanFilter(Input.acceleration.y);
    }

    void Update()
    {
        if (Input.GetKey(KeyCode.Mouse0) && Time.time > nextFire)
        {
            Instantiate(shot, shotSpawn.position, shotSpawn.rotation);
            nextFire = Time.time + fireRate;
            fireAudio.Play();
        }
    }

    void FixedUpdate()
    {
        float moveHorizontal = Input.GetAxis("Mouse X");
        float moveVertical = Input.GetAxis("Mouse Y");

        moveHorizontal = mKalmanX.Update(Input.acceleration.x);
        moveVertical = mKalmanY.Update(Input.acceleration.y);

        rb.velocity = new Vector3(moveHorizontal, moveVertical, 0) * speed;
        rb.position = new Vector3(
            Mathf.Clamp(rb.position.x, boundary.xMin, boundary.xMax)
            , Mathf.Clamp(rb.position.y, boundary.yMin, boundary.yMax)
            , 0
        );

        rb.rotation = Quaternion.Euler(0, 0, (-1) * rb.velocity.x * tilt);
    }
    
    public class KalmanFilter
    {
        private float Q = 0.00001f;
        private float R = 0.001f;
        private float X = 0, P = 1, K;

        public KalmanFilter(float initVal)
        {
            X = initVal;
        }

        private void measurementUpdate()
        {
            K = (P + Q) / (P + Q + R);
            P = R * (P + Q) / (R + P + Q);
        }

        public float Update(float measurement)
        {
            measurementUpdate();
            X = X + (measurement - X) * K;

            return X;
        }
    }
}
