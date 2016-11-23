using UnityEngine;
using System.Collections;

public class RandomRotation : MonoBehaviour {

    Rigidbody rb;
    public float tumble;
    public float speed;


    // Use this for initialization
    void Start () {
        //float x = Random.value * 8 ;
        //x -= 4;
        rb = GetComponent<Rigidbody>();
        rb.angularVelocity = Random.insideUnitSphere * tumble;
        //rb.position = new Vector3(x, 0, transform.position.z);
    }
    void Update()
    {
        
    }
}
