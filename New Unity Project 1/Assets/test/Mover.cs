using UnityEngine;
using System.Collections;

public class Mover : MonoBehaviour {

    Rigidbody rb;
    public float speed;
	
	// Update is called once per frame
	void Start () {
        rb = GetComponent<Rigidbody>();
        rb.velocity = transform.forward * speed;
	}
    void Update()
    {
    }
}
